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

import be.lmenten.sps.plugins.PluginProvider;
import be.lmenten.sps.ui.MainStageController;
import be.lmenten.utils.app.fx.FxApplication;
import be.lmenten.utils.logging.fx.LogWindow;
import be.lmenten.utils.mxparser.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.lang.Runtime.Version;
import java.util.List;
import java.util.ResourceBundle;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

/**
 *
 */
public class SeamstressPatternStudio
	extends FxApplication<SeamstressPatternStudio>
{
	public final String WORKING_DIR;

	// ------------------------------------------------------------------------
	// - WARNING: These value are updated by build script ---------------------
	// ------------------------------------------------------------------------

	public static final String APP_TITLE = "Seamstress Pattern Studio";
	public static final Version APP_VERSION = Version.parse( "1.0.1-ea0-52-devel" );
	public static final long APP_BUILD_NUMBER = 52L;
	public static final LocalDateTime APP_BUILD_DATETIME = LocalDateTime.parse( "2022-01-16T16:22:04" );

	// ------------------------------------------------------------------------
	// - Application constants ------------------------------------------------
	// ------------------------------------------------------------------------

	private final Image appIcon;

	public static final String APP_CSS_URL =
		SeamstressPatternStudio.class
			.getResource( "SeamstressPatternStudio.css" )
			.toExternalForm();

	// ------------------------------------------------------------------------

	private final ServiceLoader<PluginProvider> serviceLoader;

	private MainStageController controller;

	private ScriptEngine scriptEngine;

	// ========================================================================
	// = Constructor ==========================================================
	// ========================================================================

	private void check( Expression e )
	{
		if( !e.checkSyntax() )
		{
			System.out.println( "Syntax error in '" + e.getExpressionString() + "'" );

			String [] mm = e.getMissingUserDefinedArguments();
			for( String m : mm )
			{
				System.out.println( "   missing constant: " + m );
			}

			mm = e.getMissingUserDefinedFunctions();
			for( String m : mm )
			{
				System.out.println( "   missing function: " + m );
			}

			mm = e.getMissingUserDefinedUnits();
			for( String m : mm )
			{
				System.out.println( "   missing unit: " + m );
			}
		}
	}

	private double value = 1.0;

	public double getValue()
	{
		return value;
	}

	public SeamstressPatternStudio()
		throws IOException
	{
		WORKING_DIR = new File( "." ).getCanonicalPath();

		// --------------------------------------------------------------------
		// - Graphic resources ------------------------------------------------
		// --------------------------------------------------------------------

		InputStream is = getClass().getResourceAsStream( "images/icon48.png" );
		appIcon = new Image( is );

		// --------------------------------------------------------------------
		// - Load plugins providers -------------------------------------------
		// --------------------------------------------------------------------

		serviceLoader = ServiceLoader.load( PluginProvider.class );

		for( PluginProvider provider : getServiceLoader() )
		{
			LOG.info( "Found plugin: " + provider.getPluginName()
				+ " (id: '" +provider.getPluginIdentifier() + "', version: " + provider.getPluginVersion() + ")" );
		}
	}

	// ========================================================================
	// =
	// ========================================================================

	public ServiceLoader<PluginProvider> getServiceLoader()
	{
		return serviceLoader;
	}

	public MainStageController getMainStageController()
	{
		return controller;
	}

	private static final String SCRIPTING_LANGUAGE = "JavaScript";

	public synchronized ScriptEngine getScriptEngine()
	{
		if( scriptEngine == null )
		{
			ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
			List<ScriptEngineFactory> factories = scriptEngineManager.getEngineFactories();
			if( factories.size() == 0 )
			{
				LOG.severe( "No scripting language supported !" );
			}
			else
			{
				scriptEngine = scriptEngineManager.getEngineByName( SCRIPTING_LANGUAGE );
				if( scriptEngine == null )
				{
					LOG.severe( "Scripting language '" + SCRIPTING_LANGUAGE + "' not supported!" );
				}
			}
		}

		return scriptEngine;
	}

	// ========================================================================
	// = FxApplication interface ==============================================
	// ========================================================================

	@Override
	public String getAppTitle()
	{
		return APP_TITLE;
	}

	@Override
	public Runtime.Version getAppVersion()
	{
		return APP_VERSION;
	}

	@Override
	public Image getAppIcon()
	{
		return appIcon;
	}

	@Override
	public long getBuildNumber()
	{
		return APP_BUILD_NUMBER;
	}

	@Override
	public LocalDateTime getBuildDateTime()
	{
		return APP_BUILD_DATETIME;
	}

	// ========================================================================
	// = Application interface ================================================
	// ========================================================================

	@Override
	public void init()
		throws Exception
	{
		super.init();

		// --------------------------------------------------------------------
		// - Initialize plugins -----------------------------------------------
		// --------------------------------------------------------------------

		for( PluginProvider provider : serviceLoader )
		{
			provider.getPluginInstance().init( this );
		}
	}

	// ========================================================================
	// = Application interface = JavaFX =======================================
	// ========================================================================

	@Override
	public void start( Stage stage )
		throws Exception
	{
		super.start( stage );

		// --------------------------------------------------------------------
		// - Fine tune LogWindow ----------------------------------------------
		// --------------------------------------------------------------------

		Stage logWindowStage = LogWindow.getStage();
		if( logWindowStage != null )
		{
			logWindowStage.setTitle( logWindowStage.getTitle() + " - " + getAppTitle() );
			logWindowStage.getIcons().add( getAppIcon() );
			logWindowStage.getScene().getStylesheets().add( APP_CSS_URL );
		}

		// --------------------------------------------------------------------
		// - Create main window -----------------------------------------------
		// --------------------------------------------------------------------

		URL fxml = SeamstressPatternStudio.class.getResource( "ui/MainStage.fxml" );
		FXMLLoader loader = new FXMLLoader( fxml, RES );
		Pane root = loader.load();

		Scene scene = new Scene( root );
		scene.getStylesheets().add( APP_CSS_URL );

		stage.setTitle( getAppTitle() );
		stage.getIcons().add( getAppIcon() );
		stage.setScene( scene );
		stage.setMaximized( true );
		stage.show();

		controller = loader.getController();
		controller.setApplication( this );

		// --------------------------------------------------------------------
		// - Start plugins ----------------------------------------------------
		// --------------------------------------------------------------------

		for( PluginProvider provider : serviceLoader )
		{
			provider.getPluginInstance().start();
		}
	}

	@Override
	public void stop()
		throws Exception
	{
		for( PluginProvider provider : serviceLoader )
		{
			provider.getPluginInstance().stop();
		}

		// --------------------------------------------------------------------

		super.stop();
	}

	// ========================================================================
	// = Utilities ============================================================
	// ========================================================================

	@SuppressWarnings( "unused" )
	private static final Logger LOG
		= Logger.getLogger( SeamstressPatternStudio.class.getName() );

	@SuppressWarnings( "unused" )
	private static final Preferences PREFS
		= Preferences.userNodeForPackage( SeamstressPatternStudio.class );

	public static final ResourceBundle RES
		= ResourceBundle.getBundle( SeamstressPatternStudio.class.getName() );
}
