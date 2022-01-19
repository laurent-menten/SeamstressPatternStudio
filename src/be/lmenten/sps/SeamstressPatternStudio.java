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

import be.lmenten.sps.math.ImperialHelper;
import be.lmenten.sps.math.ImperialPrecision;
import be.lmenten.sps.plugins.PluginProvider;
import be.lmenten.sps.ui.MainStageController;
import be.lmenten.utils.app.fx.FxApplication;
import be.lmenten.utils.logging.fx.LogWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.lang.Runtime.Version;
import java.util.ResourceBundle;
import java.util.ServiceLoader;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

/**
 *
 */
public class SeamstressPatternStudio
	extends FxApplication
{
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

	// ========================================================================
	// = Constructor ==========================================================
	// ========================================================================

	public SeamstressPatternStudio()
	{
		double [] mm = { 450, 425, 157, 123, 77, 32, 25, 17 };
		for( double m : mm )
		{
			System.out.println( m + "mm = " + ImperialHelper.mmToFractionString( m, ImperialPrecision.ONE_64TH ) );
		}

		System.exit( 0 );

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
			LOG.info( "Found plugin: " + provider.getName()
				+ " (id: '" +provider.getIdentifier() + "', version: " + provider.getVersion() + ")" );
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
