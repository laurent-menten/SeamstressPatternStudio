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

import java.beans.*;
import org.jetbrains.annotations.PropertyKey;

public class BasePluginBeanInfo
	extends SimpleBeanInfo
{
	@Override
	public PropertyDescriptor[] getPropertyDescriptors()
	{
		try
		{
			// ----------------------------------------------------------------
			// - Basic
			// ----------------------------------------------------------------

			var p1 = new PropertyDescriptor( "text", BasePlugin.class );
			p1.setDisplayName( $( "property.text" ) );
			p1.setShortDescription( $( "property.text.description" ) );

			var p2 = new PropertyDescriptor( "text2", BasePlugin.class );
			p2.setDisplayName( $( "property.text2" ) );
			p2.setShortDescription( $( "property.text2.description" ) );

			// ----------------------------------------------------------------
			// - Expert
			// ----------------------------------------------------------------

			var p3 = new PropertyDescriptor( "color", BasePlugin.class );
			p3.setExpert( true );
			p3.setDisplayName( $( "property.color" ) );
			p3.setShortDescription( $( "property.color.description" ) );

			var p4 = new PropertyDescriptor( "logLevel", BasePlugin.class );
			p4.setExpert( true );
			p4.setDisplayName( $( "property.level" ) );
			p4.setShortDescription( $( "property.level.description" ) );

			return new PropertyDescriptor [] { p1, p2, p3, p4 };
		}
		catch( IntrospectionException e )
		{
			e.printStackTrace();
		}

		return new PropertyDescriptor [0];
	}

	@Override
	public MethodDescriptor[] getMethodDescriptors()
	{
		return new MethodDescriptor [0];
	}

	@Override
	public EventSetDescriptor[] getEventSetDescriptors()
	{
		return new EventSetDescriptor [0];
	}

	// ========================================================================
	// = Utilities ============================================================
	// ========================================================================

	private String $( @PropertyKey( resourceBundle="be.lmenten.sps.plugins.base.BasePlugin" ) String key )
	{
		return BasePlugin.RES.getString( key );
	}
}
