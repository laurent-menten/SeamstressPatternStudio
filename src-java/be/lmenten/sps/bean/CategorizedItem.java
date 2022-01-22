package be.lmenten.sps.bean;

import javafx.beans.value.ObservableValue;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.PropertyEditor;

import java.util.Optional;

public class CategorizedItem
	implements PropertySheet.Item
{
	// ========================================================================
	// =
	// ========================================================================

	public CategorizedItem()
	{

	}

	// ========================================================================
	// =
	// ========================================================================

	@Override
	public Class<?> getType()
	{
		return null;
	}

	@Override
	public String getCategory()
	{
		return null;
	}

	@Override
	public String getName()
	{
		return null;
	}

	@Override
	public String getDescription()
	{
		return null;
	}

	@Override
	public Object getValue()
	{
		return null;
	}

	@Override
	public void setValue( Object value )
	{

	}

	@Override
	public Optional<ObservableValue<? extends Object>> getObservableValue()
	{
		return Optional.empty();
	}

	@Override
	public Optional<Class<? extends PropertyEditor<?>>> getPropertyEditorClass()
	{
		return PropertySheet.Item.super.getPropertyEditorClass();
	}

	@Override
	public boolean isEditable()
	{
		return PropertySheet.Item.super.isEditable();
	}
}
