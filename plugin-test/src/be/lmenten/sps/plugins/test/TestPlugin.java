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

package be.lmenten.sps.plugins.test;

import be.lmenten.sps.SeamstressPatternStudio;
import be.lmenten.sps.plugins.Plugin;
import be.lmenten.sps.tools.ToolCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.BeanPropertyUtils;

import java.util.logging.Logger;

public class TestPlugin
	implements Plugin
{
	private SeamstressPatternStudio app;

	private final PropertySheet propertySheet
		= new PropertySheet();

	private final ObservableList<PropertySheet.Item> properties
			= FXCollections.observableArrayList();

	// ========================================================================
	// = Constructor ==========================================================
	// ========================================================================

	public TestPlugin()
	{
	}

	// ========================================================================
	// = Plugin interface =====================================================
	// ========================================================================

	@Override
	public void init( SeamstressPatternStudio app )
	{
		LOG.info( TestPluginProvider.PLUGIN_IDENTIFIER + ": init()" );

		this.app = app;

		var properties = BeanPropertyUtils.getProperties( this );
		propertySheet.getItems().addAll( properties );
	}

	@Override
	public void start()
	{
		LOG.info( TestPluginProvider.PLUGIN_IDENTIFIER + ": start()" );

		app.getMainStageController().addTool( ToolCategory.OTHER, "other" );
	}

	@Override
	public void stop()
	{
		LOG.info( TestPluginProvider.PLUGIN_IDENTIFIER + ": stop()" );
	}

	// ------------------------------------------------------------------------

	@Override
	public Node getSettingsPanel()
	{
		return null; //propertySheet;
	}

	// ========================================================================
	// = Utilities ============================================================
	// ========================================================================

	@SuppressWarnings( "unused" )
	private static final Logger LOG
			= Logger.getLogger( TestPlugin.class.getName() );
}
