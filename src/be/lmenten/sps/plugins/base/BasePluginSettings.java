package be.lmenten.sps.plugins.base;

import be.lmenten.utils.logging.LogLevel;
import be.lmenten.utils.settings.Settings;
import be.lmenten.utils.settings.SettingsBeanInfo;
import javafx.scene.paint.Color;

public class BasePluginSettings
	implements Settings
{
	private String text;
	private String text2;
	private Color color;
	private LogLevel logLevel;

	// ========================================================================
	// = Constructor ==========================================================
	// ========================================================================

	public BasePluginSettings()
	{
		setText( "default 1" );
		setText2( "default 2" );
		setColor( Color.BLUEVIOLET );
		setLogLevel( LogLevel.WARNING );
	}

	// ========================================================================
	// = Properties accessors =================================================
	// ========================================================================

	public String getText()
	{
		return text;
	}

	public void setText( String text )
	{
		this.text = text;
	}

	// ------------------------------------------------------------------------

	public String getText2()
	{
		return text2;
	}

	public void setText2( String text2 )
	{
		this.text2 = text2;
	}

	// ------------------------------------------------------------------------

	public Color getColor()
	{
		return color;
	}

	public void setColor( Color color )
	{
		this.color = color;
	}

	// ------------------------------------------------------------------------

	public LogLevel getLogLevel()
	{
		return logLevel;
	}

	public void setLogLevel( LogLevel logLevel )
	{
		this.logLevel = logLevel;
	}

	// ========================================================================
	// = Settings interface ===================================================
	// ========================================================================

	@Override
	public void load()
	{
	}

	@Override
	public void save()
	{
	}
}
