package be.lmenten.sps.plugins.test;

import be.lmenten.utils.settings.SettingsBeanInfo;
import org.jetbrains.annotations.PropertyKey;

import java.beans.PropertyDescriptor;

public class TestPluginSettingsBeanInfo
	extends SettingsBeanInfo
{
	@Override
	public PropertyDescriptor[] getPropertyDescriptors()
	{
		return new PropertyDescriptor[ 0 ];
	}

	@Override
	protected String $( @PropertyKey( resourceBundle="be.lmenten.sps.plugins.test.TestPlugin" ) String key )
	{
		return TestPlugin.RES.getString( key );
	}
}
