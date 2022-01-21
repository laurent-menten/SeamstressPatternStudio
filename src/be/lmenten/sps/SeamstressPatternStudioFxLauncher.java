/* ************************************************************************* *
 *    Seamstress Pattern Studio    +    Copyright (c)2022+ Laurent Menten    *
 * ************************************************************************* *
 * This program is free software: you can redistribute it and/or modify it   *
 * under the terms of the GNU General Public License as published by the     *
 * Free Software Foundation, either version 3 of the License, or (at your    *
 * option) any later version.                                                *
 *                                                                           *
 * This program is distributed in the hope that it will be useful, but       *
 * WITHOUT ANY WARRANTY; without even the implied warranty of                *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General *
 * Public License for more details.                                          *
 *                                                                           *
 * You should have received a copy of the GNU General Public License along   *
 * with this program. If not, see <https://www.gnu.org/licenses/>.           *
 * ************************************************************************* */

package be.lmenten.sps;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import be.lmenten.sps.tmp.calc.Calc;
import javafx.application.Application;

/**
 * <P>
 * Launcher for JavaFx application from jar.
 * </P>
 *
 * <P>
 * Get fx path from PATH_TO_FX environment variable and construct a java
 * command line with the fx required modules, then start application.
 * </P>
 *
 * @version 1.0.1
 * @since 2022-01-02
 * @author <A HREF="mailto:laurent.menten@gmail.com">Laurent MENTEN</A>
 */
public class SeamstressPatternStudioFxLauncher
{
	private static final boolean debugMode
		= Boolean.parseBoolean(
			System.getProperty( "be.lmenten.debug", "false" )
		);

	// ========================================================================
	// = Proxy class ==========================================================
	// ========================================================================

	/**
	 * Proxy to avoid unresolved fx classes error at launcher run.
	 */
	static class Proxy
	{
		public static void main( String[] args )
		{
			long start = System.currentTimeMillis();

			Application.launch( SeamstressPatternStudio.class, args );
//			Application.launch( Calc.class, args );
//			Application.launch( Paint.class, args );
//			Application.launch( Zoom.class, args );

			long duration = System.currentTimeMillis() - start;

			// ----------------------------------------------------------------

			long millis = duration % 1000;
			long second = (duration / 1000) % 60;
			long minute = (duration / (1000 * 60)) % 60;
			long hour = (duration / (1000 * 60 * 60)) % 24;
			String time = String.format( "%02d:%02d:%02d.%d", hour, minute, second, millis );

			LOG.info( "Application finished." );
			LOG.info( "Running time : " + time );

			// ----------------------------------------------------------------

			if( debugMode )
			{
				Toolkit.getDefaultToolkit().beep();
			}
		}
	}

	// ========================================================================
	// = Main =================================================================
	// ========================================================================

	public static void main( String[] args )
			throws URISyntaxException, IOException
	{
		final List<String> javaArgs = new ArrayList<>();

		// --------------------------------------------------------------------
		// - Check OS ---------------------------------------------------------
		// --------------------------------------------------------------------

		boolean isWindows =
			System.getProperty( "os.name" ).toLowerCase().contains( "win" );

		// --------------------------------------------------------------------
		// - Check if running from jar ----------------------------------------
		// --------------------------------------------------------------------

		String protocol =
			Objects.requireNonNull( Proxy.class.getResource("") ).getProtocol();

		boolean runsFromJar = "jar".equalsIgnoreCase( protocol );

		// --------------------------------------------------------------------
		// - Java VM executable path ------------------------------------------
		// --------------------------------------------------------------------

		String java = System.getProperty( "java.home" )
			+ File.separator + "bin"
			+ File.separator + "java"
			;

		if( isWindows )
		{
			java += ".exe";
		}

		javaArgs.add( java );

		// --------------------------------------------------------------------
		// - Set acceptable defaults for debug mode ---------------------------
		// --------------------------------------------------------------------

		if( debugMode )
		{
			javaArgs.add( "-Dbe.lmenten.debug=true" );
			javaArgs.add( "-Dbe.lmenten.ansiLogOutput=true" );
			javaArgs.add( "-Dbe.lmenten.logLevel=ALL" );
			javaArgs.add( "-Dbe.lmenten.logFilter=be.lmenten.*" );
			javaArgs.add( "-Dbe.lmenten.showLogWindow=true" );
		}

		// --------------------------------------------------------------------
		// - Add original VM arguments ----------------------------------------
		// --------------------------------------------------------------------

		RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
		List<String> jvmArgs = runtime.getInputArguments();

		javaArgs.addAll( jvmArgs );

		// --------------------------------------------------------------------
		// - Working directory ------------------------------------------------
		// --------------------------------------------------------------------

		URI jarUrl = Proxy.class.getProtectionDomain().getCodeSource().getLocation().toURI();

		File jarFile = new File( jarUrl );
		String jarPath = jarFile.getParentFile().getAbsolutePath();

		// --------------------------------------------------------------------

		String workingDir;
		if( runsFromJar )
		{
			workingDir = jarFile.getParentFile().getCanonicalPath();
		}
		else
		{
			workingDir = new File( "." ).getCanonicalPath();
		}

		// --------------------------------------------------------------------
		// - module-path and module lists -------------------------------------
		// --------------------------------------------------------------------

		String fxDirName =  System.getenv( "PATH_TO_FX" );
		if( fxDirName == null )
		{
			error( "JavaFx PATH_TO_FX environment variable not found.", null );
		}

		String modulePath =
				workingDir
			+	File.pathSeparator + workingDir + File.separator + "lib"
			+	File.pathSeparator + workingDir + File.separator + "plugins"
			+	File.pathSeparator + fxDirName
			;

		javaArgs.add( "--module-path" );
		javaArgs.add( "\"" +modulePath + "\"" );

		// --------------------------------------------------------------------

		String moduleList = ""
			+	      "lib.lmenten"
			+	"," + "lib.lmenten.fx"
			+	"," + "org.jetbrains.annotations"

			+	"," + "mXparser"
			+	"," + "org.controlsfx.controls"

			+	"," + "javafx.base"
			+	"," + "javafx.graphics"
			+	"," + "javafx.controls"
			+	"," + "javafx.web"
			+	"," + "javafx.fxml"
			;

		javaArgs.add( "--add-modules" );
		javaArgs.add( moduleList );

//		javaArgs.add( "--add-exports" );
//		javaArgs.add( "javafx.web/com.sun.webkit.dom=SeamstressPatternStudio" );

		// --------------------------------------------------------------------
		// - Plugins list + Classpath and class name for proxy ----------------
		// --------------------------------------------------------------------

		String pluginsList = "";

		File pluginsDir = new File(workingDir + File.separator + "plugins" );
		File[] plugins = pluginsDir.listFiles( ( dir, name )
			-> name.toLowerCase().endsWith( ".jar" )
		);

		for( File plugin : plugins )
		{
			pluginsList += File.pathSeparator + plugin.getAbsolutePath();
		}

		// --------------------------------------------------------------------

		javaArgs.add( "-classpath" );
		if( runsFromJar )
		{
			javaArgs.add( "\"" + jarFile.getAbsolutePath() + pluginsList + "\"" );
		}
		else
		{
			javaArgs.add( "\"" + jarPath + pluginsList + "\"" );
		}

		javaArgs.add( Proxy.class.getName() );

		// --------------------------------------------------------------------
		// - Add program arguments --------------------------------------------
		// --------------------------------------------------------------------

		Collections.addAll( javaArgs, args );

		// --------------------------------------------------------------------
		// - Debug ------------------------------------------------------------
		// --------------------------------------------------------------------

		if( debugMode )
		{
			System.out.println( "Working directory: '" + workingDir + "'." );

			for( int i = 0 ; i < javaArgs.size() ;  i++ )
			{
				System.out.printf( "   %2d: %s\n", i, javaArgs.get( i ) );
			}
		}

		// --------------------------------------------------------------------
		// - Run process ------------------------------------------------------
		// --------------------------------------------------------------------

		try
		{
			ProcessBuilder pb = new ProcessBuilder( javaArgs );
			pb.directory( new File( workingDir ) );
			pb.inheritIO();

			Process p = pb.start();

			int exitCode = p.waitFor();

			System.exit( exitCode );
		}
		catch( Exception ex )
		{
			error( "Failed to launch application process.", ex );
		}
	}

	// ========================================================================
	// = Error handling =======================================================
	// ========================================================================

	private static void error( String message, Throwable ex )
	{
		if( GraphicsEnvironment.isHeadless() )
		{
			System.err.println( message );

			if( ex != null )
			{
				ex.printStackTrace();
			}
		}
		else
		{
			if( ex != null )
			{
				message += "\n" + ex.getMessage();
			}

			JOptionPane.showMessageDialog( null,
				message,
				Proxy.class.getSimpleName(),
				JOptionPane.ERROR_MESSAGE
			);
		}

		System.exit( -1 );
	}

	// ========================================================================
	// = Utilities ============================================================
	// ========================================================================

	private static final Logger LOG
		= Logger.getLogger( SeamstressPatternStudioFxLauncher.class.getName() );
}
