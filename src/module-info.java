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

import be.lmenten.sps.plugins.PluginProvider;
import be.lmenten.sps.plugins.base.BasePluginProvider;

module SeamstressPatternStudio
{
	requires lib.lmenten;
	requires lib.lmenten.fx;

	requires la4j;
	requires exp4j;
	requires javaluator;

	requires java.desktop;
	requires java.logging;
	requires java.management;
	requires java.prefs;

	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;

	requires org.controlsfx.controls;
	requires org.jetbrains.annotations;

	exports be.lmenten.sps;
	exports be.lmenten.sps.plugins;
	exports be.lmenten.sps.plugins.base;
	exports be.lmenten.sps.tools;
	exports be.lmenten.sps.ui;

	opens be.lmenten.sps.ui to javafx.fxml;

	exports be.lmenten.sps.tmp.bezier to javafx.graphics;
	opens be.lmenten.sps.tmp.bezier to javafx.fxml;

	uses be.lmenten.sps.plugins.PluginProvider;
	provides PluginProvider with
		BasePluginProvider;
}