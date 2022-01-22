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
import be.lmenten.sps.plugins.base.BasePlugin;
import be.lmenten.sps.tools.ToolCategory;
import be.lmenten.utils.settings.Settings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.BeanPropertyUtils;
import org.jetbrains.annotations.PropertyKey;

import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

public class TestPlugin
	implements Plugin
{
	private final TestPluginSettings settings
		= new TestPluginSettings();

	private final PropertySheet propertySheet
		= new PropertySheet();

	// ------------------------------------------------------------------------

	private SeamstressPatternStudio app;

	private final ObservableList<PropertySheet.Item> properties
		= FXCollections.observableArrayList();

	// ========================================================================
	// = Constructor ==========================================================
	// ========================================================================

	public TestPlugin()
	{
		settings.load();
	}

	// ========================================================================
	// = Plugin interface =====================================================
	// ========================================================================

	@Override
	public void init( SeamstressPatternStudio app )
	{
		LOG.info( TestPluginProvider.PLUGIN_IDENTIFIER + ": init()" );

		this.app = app;

		propertySheet.setMode( PropertySheet.Mode.CATEGORY );
		propertySheet.getItems()
			.addAll( BeanPropertyUtils.getProperties( settings ) );
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
		return propertySheet;
	}

	// ========================================================================
	// = Utilities ============================================================
	// ========================================================================
	// ========================================================================
	// = Utilities ============================================================
	// ========================================================================

	@SuppressWarnings( "unused" )
	private static final Logger LOG
		= Logger.getLogger( TestPlugin.class.getName() );

	@SuppressWarnings( "unused" )
	private static final Preferences PREFS
		= Preferences.userNodeForPackage( TestPlugin.class );

	/*package*/ static final ResourceBundle RES
		= ResourceBundle.getBundle( TestPlugin.class.getName() );

	private String $( @PropertyKey( resourceBundle="be.lmenten.sps.plugins.test.TestPlugin" ) String key )
	{
		return RES.getString( key );
	}
}
