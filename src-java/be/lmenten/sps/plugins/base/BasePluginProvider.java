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

import be.lmenten.sps.plugins.AbstractPluginProvider;
import be.lmenten.sps.plugins.Plugin;
import be.lmenten.sps.plugins.PluginType;
import org.jetbrains.annotations.PropertyKey;

import java.lang.Runtime.Version;

public class BasePluginProvider
	extends AbstractPluginProvider
{
	// ========================================================================
	// = PluginProvider interface =============================================
	// ========================================================================

	@Override
	public String getPluginIdentifier()
	{
		return BasePlugin.PLUGIN_IDENTIFIER;
	}

	@Override
	public String getPluginName()
	{
		return BasePlugin.PLUGIN_NAME;
	}

	@Override
	public String getPluginDescription()
	{
		return $( "plugin.identifier" );
	}

	@Override
	public Version getPluginVersion()
	{
		return BasePlugin.PLUGIN_VERSION;
	}

	@Override
	public PluginType getPluginType()
	{
		return BasePlugin.PLUGIN_TYPE;
	}

	// ------------------------------------------------------------------------
	// - Plugin instance ------------------------------------------------------
	// ------------------------------------------------------------------------

	private BasePlugin instance = null;

	@Override
	public synchronized Plugin getPluginInstance()
	{
		if( instance == null )
		{
			instance = new BasePlugin();
		}

		return instance;
	}

	// ========================================================================
	// = Utilities ============================================================
	// ========================================================================

	private String $( @PropertyKey( resourceBundle=BasePlugin.PLUGIN_RESOURCE_FQN ) String key )
	{
		return BasePlugin.RES.getString( key );
	}
}
