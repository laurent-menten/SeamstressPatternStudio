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

package be.lmenten.sps.plugins.base;

import be.lmenten.sps.SeamstressPatternStudio;
import be.lmenten.sps.plugins.AbstractPlugin;
import be.lmenten.sps.plugins.PluginType;
import be.lmenten.sps.tools.ToolCategory;
import javafx.scene.Node;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.BeanPropertyUtils;
import org.jetbrains.annotations.PropertyKey;

import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import java.lang.Runtime.Version;

public class BasePlugin
	extends AbstractPlugin
{
	// ------------------------------------------------------------------------
	// - PluginProvider support -----------------------------------------------
	// ------------------------------------------------------------------------

	/*package*/ static final String PLUGIN_IDENTIFIER = "sps-base-test";
	/*package*/ static final String PLUGIN_NAME = "Base plugin (test)";
	/*package*/ static final Version PLUGIN_VERSION = Runtime.Version.parse(  "1.0.1" );
	/*package*/ static final PluginType PLUGIN_TYPE = PluginType.DUMMY;

	/*package*/ static final String PLUGIN_RESOURCE_FQN = "be.lmenten.sps.plugins.base.BasePlugin";

	// ------------------------------------------------------------------------

	private final BasePluginSettings settings
		= new BasePluginSettings();

	private final PropertySheet propertySheet
		= new PropertySheet();

	// ========================================================================
	// = Constructor ==========================================================
	// ========================================================================

	public BasePlugin()
	{
		settings.load();

		propertySheet.setMode( PropertySheet.Mode.CATEGORY );
		propertySheet.getItems()
			.addAll( BeanPropertyUtils.getProperties( settings ) );
	}

	// ========================================================================
	// = Plugin interface =====================================================
	// ========================================================================

	@Override
	public void init( SeamstressPatternStudio app )
		throws Exception
	{
		LOG.info( PLUGIN_IDENTIFIER + ": init()" );

		super.init( app );
	}

	@Override
	public void start()
		throws Exception
	{
		LOG.info( PLUGIN_IDENTIFIER + ": start()" );

		app.getMainStageController().addTool( ToolCategory.DOT, "dot" );
		app.getMainStageController().addTool( ToolCategory.LINE, "line" );
	}

	@Override
	public void stop()
		throws Exception
	{
		LOG.info( PLUGIN_IDENTIFIER + ": stop()" );
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

	@SuppressWarnings( "unused" )
	private static final Logger LOG
		= Logger.getLogger( BasePlugin.class.getName() );

	@SuppressWarnings( "unused" )
	private static final Preferences PREFS
		= Preferences.userNodeForPackage( BasePlugin.class );

	/*package*/ static final ResourceBundle RES
		= ResourceBundle.getBundle( BasePlugin.class.getName() );

	private String $( @PropertyKey( resourceBundle=PLUGIN_RESOURCE_FQN ) String key )
	{
		return RES.getString( key );
	}
}
