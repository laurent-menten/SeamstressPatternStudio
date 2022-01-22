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

import be.lmenten.sps.plugins.AbstractPluginProvider;
import be.lmenten.sps.plugins.Plugin;
import be.lmenten.sps.plugins.PluginProvider;
import be.lmenten.sps.plugins.PluginType;
import javafx.scene.control.Button;
import javafx.scene.control.Control;

public class TestPluginProvider
	extends AbstractPluginProvider
{
	/*package*/ static final String PLUGIN_IDENTIFIER = "plugin-test";
	/*package*/ static final String PLUGIN_NAME = "Test plugin";
	/*package*/ static final Runtime.Version PLUGIN_VERSION = Runtime.Version.parse(  "1.0.1" );

	private TestPlugin instance;

	@Override
	public String getPluginIdentifier()
	{
		return PLUGIN_IDENTIFIER;
	}

	@Override
	public String getPluginName()
	{
		return PLUGIN_NAME;
	}

	@Override
	public String getPluginDescription()
	{
		return null;
	}

	@Override
	public Runtime.Version getPluginVersion()
	{
		return PLUGIN_VERSION;
	}

	@Override
	public PluginType getPluginType()
	{
		return PluginType.DUMMY;
	}

	@Override
	public Plugin getPluginInstance()
	{
		if( instance == null )
		{
			instance = new TestPlugin();
		}

		return instance;
	}
}
