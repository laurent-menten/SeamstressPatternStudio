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

package be.lmenten.sps.ui;

import be.lmenten.sps.SeamstressPatternStudio;
import be.lmenten.sps.tools.ToolCategory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.control.StatusBar;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;
import org.controlsfx.property.BeanPropertyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class MainStageController
	implements Initializable
{
	private SeamstressPatternStudio application;

	// ------------------------------------------------------------------------

	@FXML private MenuBar menuBar;

	@FXML private ToolBar appToolBar;
	@FXML private Button 	butCalculator;
	@FXML private Button 	butSettings;

	@FXML private ToolBar patternToolBar;

	@FXML private ToolBar drawingToolBar;

	@FXML private StatusBar statusBar;

	@FXML private Accordion toolsAccording;

	@FXML private TabPane sheetsPane;

	@FXML private PropertySheet currentSheetProperties;

	@FXML private PropertySheet currentObjectProperties;

	// ========================================================================
	// = Constructor ==========================================================
	// ========================================================================

	public MainStageController()
	{
	}

	@Override
	public void initialize( URL url, ResourceBundle resourceBundle )
	{
		GlyphFont fontAwesome = GlyphFontRegistry.font("FontAwesome" );

		butCalculator.setGraphic( fontAwesome.create( FontAwesome.Glyph.CALCULATOR ) );
		butSettings.setGraphic( fontAwesome.create( FontAwesome.Glyph.COG ) );
	}

	// ------------------------------------------------------------------------

	public void setApplication( SeamstressPatternStudio application )
	{
		this.application = application;
	}

	// ========================================================================
	// =
	// ========================================================================

	/**
	 * Create a button for the tool in the accordion TitledPane for the given
	 * category.
	 *
	 * @param category the category of the tool
	 * @param arg something...
	 */
	public void addTool( ToolCategory category, Object arg )
	{
		System.out.println( category + ": " + arg );
	}

	// ========================================================================
	// =
	// ========================================================================

	@FXML private void onCalculator( ActionEvent ev )
	{

	}

	@FXML private void onSettings( ActionEvent ev )
		throws IOException
	{
		// --------------------------------------------------------------------
		// - Create settings modal window -------------------------------------
		// --------------------------------------------------------------------

		URL fxml = SeamstressPatternStudio.class.getResource( "ui/Settings.fxml" );
		FXMLLoader loader = new FXMLLoader( fxml, SeamstressPatternStudio.RES );
		Pane root = loader.load();

		Scene scene = new Scene( root );
		scene.getStylesheets().add( SeamstressPatternStudio.APP_CSS_URL );

		Stage stage = new Stage();
		stage.setTitle( "Settings" );
		stage.getIcons().add( application.getAppIcon() );
		stage.setScene( scene );

		stage.initModality( Modality.WINDOW_MODAL);
		stage.initOwner( ((Node)ev.getSource()).getScene().getWindow() );

		// --------------------------------------------------------------------
		// - Add plugins settings panel ---------------------------------------
		// --------------------------------------------------------------------

		SettingsController controller = loader.getController();

		PropertySheet propertySheet = new PropertySheet();
		propertySheet.setMode( PropertySheet.Mode.CATEGORY );
		propertySheet.getItems()
				.addAll( BeanPropertyUtils.getProperties( application.getSettings() ) );
		controller.addPluginSettingsPanel( "FxApplication", propertySheet );

//		application.getServiceLoader().forEach( provider ->
//			controller.addPluginSettingsPanel( provider.getName(), provider.getPluginInstance().getSettingsPanel() )
//		);

		stage.showAndWait();

		LOG.info( "Settings done" );

		// --------------------------------------------------------------------
		// - Save changes -----------------------------------------------------
		// --------------------------------------------------------------------

		application.getSettings().saveToPreferences();
	}

	// ========================================================================
	// = Utilities ============================================================
	// ========================================================================

	private static final Logger LOG
		= Logger.getLogger( MainStageController.class.getName() );
}