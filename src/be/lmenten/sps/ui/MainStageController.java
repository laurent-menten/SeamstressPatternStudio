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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.control.StatusBar;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;
import org.controlsfx.property.BeanPropertyUtils;
import org.jetbrains.annotations.NotNull;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class MainStageController
	implements Initializable
{
	private SeamstressPatternStudio application;

	// ------------------------------------------------------------------------

	@FXML private BorderPane root;

	@FXML private MenuBar menuBar;

	@FXML private ToolBar appToolBar;
	@FXML private Button 	butCalculator;
	@FXML private Button 	butSettings;
	@FXML private Button 	butHelp;

	@FXML private ToolBar patternToolBar;

	@FXML private ToolBar drawingToolBar;

	@FXML private StatusBar statusBar;

	@FXML private Accordion toolsAccording;

	@FXML private TabPane sheetsPane;

	@FXML private PropertySheet currentSheetProperties;

	@FXML private PropertySheet currentObjectProperties;

	@FXML private TextArea scriptInput;

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
		LOG.info( "Adding tool: " + category + "/" + arg.toString() );
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

		application.getServiceLoader().forEach( provider ->
			controller.addPluginSettingsPanel( provider.getPluginName(), provider.getPluginInstance().getSettingsPanel() )
		);

		stage.showAndWait();

		LOG.info( "Settings done" );

		// --------------------------------------------------------------------
		// - Save changes -----------------------------------------------------
		// --------------------------------------------------------------------

		application.getSettings().saveToPreferences();
	}

	@FXML void onHelp( ActionEvent ev )
		throws IOException
	{
		// --------------------------------------------------------------------
		// - Create settings modal window -------------------------------------
		// --------------------------------------------------------------------

		URL fxml = SeamstressPatternStudio.class.getResource( "ui/Help.fxml" );
		FXMLLoader loader = new FXMLLoader( fxml, SeamstressPatternStudio.RES );
		Pane root = loader.load();

		Scene scene = new Scene( root );
		scene.getStylesheets().add( SeamstressPatternStudio.APP_CSS_URL );

		Stage stage = new Stage();
		stage.setTitle( "Help" );
		stage.getIcons().add( application.getAppIcon() );
		stage.setScene( scene );

		stage.initModality( Modality.NONE );
		stage.initOwner( ((Node)ev.getSource()).getScene().getWindow() );

		stage.show();
	}

	// ========================================================================
	// = Scripting engine =====================================================
	// ========================================================================

	private Writer outWriter = new Writer()
	{
		@Override
		public void write( @NotNull char[] cbuf, int off, int len ) throws IOException
		{
			scriptInput.appendText( "Out: " + new String( cbuf ) + "\n" );
		}

		@Override
		public void flush() throws IOException
		{

		}

		@Override
		public void close() throws IOException
		{

		}
	};

	private Writer errWriter = new Writer()
	{
		@Override
		public void write( @NotNull char[] cbuf, int off, int len ) throws IOException
		{
			scriptInput.appendText( "Err: " + new String( cbuf ) + "\n" );
		}

		@Override
		public void flush() throws IOException
		{

		}

		@Override
		public void close() throws IOException
		{

		}
	};

	// ------------------------------------------------------------------------

	@FXML void onScriptKeyPressed( KeyEvent ev )
	{
		if( ev.getCode().equals( KeyCode.ENTER ) && ev.isControlDown() )
		{
			onScriptExecute( null );
		}
	}

	@FXML void onScriptExecute( ActionEvent ev )
	{
		LOG.info( "Execute script" );

		scriptInput.appendText( "\n" );

		ScriptEngine engine = application.getScriptEngine();
		if( engine != null )
		{
			try
			{
				engine.getContext().setWriter( outWriter );
				engine.getContext().setErrorWriter( errWriter );
				engine.eval( scriptInput.getText() );
			}
			catch( ScriptException ex )
			{
				LOG.log( Level.SEVERE, "Script error", ex );

				Alert alert = new Alert( Alert.AlertType.CONFIRMATION );
				alert.initOwner( root.getScene().getWindow() );
				alert.initModality( Modality.APPLICATION_MODAL );

				alert.setTitle( application.getAppTitle() );

				alert.setHeaderText( "Scripting error" );
				alert.setContentText( ex.getMessage() );

				ButtonType okButton = new ButtonType( "OK", ButtonBar.ButtonData.YES );
				alert.getButtonTypes().setAll( okButton );

				alert.showAndWait();
			}
		}
	}

	@FXML void onScriptClear( ActionEvent ev )
	{
		scriptInput.clear();
	}

	@FXML void onScriptPrevious( ActionEvent ev )
	{
	}

	@FXML void onScriptNext( ActionEvent ev )
	{
	}

	@FXML void onScriptClearHistory( ActionEvent ev )
	{
	}

	// ========================================================================
	// = Utilities ============================================================
	// ========================================================================

	private static final Logger LOG
		= Logger.getLogger( MainStageController.class.getName() );
}