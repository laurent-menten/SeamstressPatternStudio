package be.lmenten.sps.plugins.base;

import be.lmenten.utils.settings.SettingsBeanInfo;
import org.jetbrains.annotations.PropertyKey;

import java.beans.*;

public class BasePluginSettingsBeanInfo
	extends SettingsBeanInfo
{
	// ========================================================================
	// =
	// ========================================================================

	@Override
	public PropertyDescriptor[] getPropertyDescriptors()
	{
		try
		{
			// ----------------------------------------------------------------
			// - Basic --------------------------------------------------------
			// ----------------------------------------------------------------

			var p1 = new PropertyDescriptor( "text", BasePluginSettings.class );
			p1.setDisplayName( $( "property.text" ) );
			p1.setShortDescription( $( "property.text.description" ) );

			var p2 = new PropertyDescriptor( "text2", BasePluginSettings.class );
			p2.setDisplayName( $( "property.text2" ) );
			p2.setShortDescription( $( "property.text2.description" ) );

			// ----------------------------------------------------------------
			// - Expert -------------------------------------------------------
			// ----------------------------------------------------------------

			var p3 = new PropertyDescriptor( "color", BasePluginSettings.class );
			p3.setExpert( true );
			p3.setDisplayName( $( "property.color" ) );
			p3.setShortDescription( $( "property.color.description" ) );

			var p4 = new PropertyDescriptor( "logLevel", BasePluginSettings.class );
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

	// ========================================================================
	// = Utilities ============================================================
	// ========================================================================

	@Override
	protected String $( @PropertyKey( resourceBundle="be.lmenten.sps.plugins.base.BasePlugin" ) String key )
	{
		return BasePlugin.RES.getString( key );
	}
}
