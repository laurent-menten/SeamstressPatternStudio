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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 *
 */
// TODO implement webview resize when window is resized.
public class HelpController
	implements Initializable, ChangeListener
{
	@FXML private StackPane stackPane;
	@FXML private WebView webView;

	// ========================================================================
	// = Constructor ==========================================================
	// ========================================================================

	public HelpController()
	{
	}

	@Override
	public void initialize( URL url, ResourceBundle resourceBundle )
	{
		stackPane.heightProperty().addListener( this );
		stackPane.widthProperty().addListener( this );

		WebEngine webEngine = webView.getEngine();
		webEngine.getLoadWorker().stateProperty().addListener( this );

		URL helpUrl = SeamstressPatternStudio.class.getResource( "docs/index.html" );
		if( helpUrl != null )
		{
			webEngine.load( helpUrl.toExternalForm() );
		}
		else
		{
			LOG.warning( "Could not find documentation" );

			String message = """
   			<h1>ERROR: Could not find documentation</h1>
			""";

			webEngine.loadContent( message, "text/html" );
		}
	}

	// ========================================================================
	// = ChangeListener interface =============================================
	// ========================================================================

	@Override
	public void changed( ObservableValue observableValue, Object oldValue, Object newValue )
	{
		if( observableValue == stackPane.heightProperty() )
		{
			webView.setPrefHeight( (Double)newValue );
			webView.autosize();
		}

		if( observableValue == stackPane.widthProperty() )
		{
			webView.setPrefWidth( (Double)newValue );
			webView.autosize();
		}
	}

	// ========================================================================
	// = Utilities ============================================================
	// ========================================================================

	private static final Logger LOG
		= Logger.getLogger( HelpController.class.getName() );
}
