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

import be.lmenten.sps.plugins.Plugin;
import be.lmenten.sps.plugins.PluginProvider;
import be.lmenten.sps.plugins.PluginType;

import java.lang.Runtime.Version;

public class BasePluginProvider
	implements PluginProvider
{
	/*package*/ static final String PLUGIN_IDENTIFIER = "plugin-base";
	/*package*/ static final String PLUGIN_NAME = "Base plugin";
	/*package*/ static final Version PLUGIN_VERSION = Version.parse(  "1.0.1" );

	private BasePlugin instance = null;

	@Override
	public String getIdentifier()
	{
		return PLUGIN_IDENTIFIER;
	}

	@Override
	public String getName()
	{
		return PLUGIN_NAME;
	}

	@Override
	public String getDescription()
	{
		return null;
	}

	@Override
	public Version getVersion()
	{
		return PLUGIN_VERSION;
	}

	@Override
	public PluginType getType()
	{
		return PluginType.DUMMY;
	}

	@Override
	public Plugin getPluginInstance()
	{
		if( instance == null )
		{
			instance = new BasePlugin();
		}

		return instance;
	}
}
