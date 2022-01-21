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

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class SettingsController
	implements Initializable
{
	@FXML
	public AnchorPane pane;

	// ========================================================================
	// = Constructor ==========================================================
	// ========================================================================

	public SettingsController()
	{
	}

	@Override
	public void initialize( URL url, ResourceBundle resourceBundle )
	{
	}

	// ========================================================================
	// =
	// ========================================================================

	void addPluginSettingsPanel( String pluginName, Node pluginSettingsPanel )
	{
		if( pluginSettingsPanel != null )
		{
			pane.getChildren().add( pluginSettingsPanel );
		}
	}
	// ========================================================================
	// = Utilities ============================================================
	// ========================================================================

	private static final Logger LOG
		= Logger.getLogger( SettingsController.class.getName() );
}
