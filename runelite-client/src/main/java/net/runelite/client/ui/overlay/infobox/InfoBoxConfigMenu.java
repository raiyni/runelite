/*
 * Copyright (c) 2020, Ron Young <https://github.com/raiyni>
 * All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *     list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.runelite.client.ui.overlay.infobox;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ItemEvent;
import java.util.function.BiConsumer;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import net.runelite.api.Item;
import net.runelite.client.ui.ColorScheme;
import org.pushingpixels.substance.internal.SubstanceSynapse;

class InfoBoxConfigMenu extends JDialog
{
	private InfoBox currentInfoBox;
	private final JComboBox<String> comboBox;
	private final LayerManager layerManager;

	private static class ItemEditor extends BasicComboBoxEditor
	{
		public void setItem(Object anObject)
		{
			Item item = (Item) anObject;
			editor.setText(item.getId() + "");
		}
	}

	public InfoBoxConfigMenu(Frame parent, LayerManager layerManager, InfoBox infoBox, BiConsumer<String, InfoBox> consumer)
	{
		super(parent, "Configure InfoBox", ModalityType.MODELESS);
		this.currentInfoBox = infoBox;
		this.layerManager = layerManager;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBackground(ColorScheme.PROGRESS_COMPLETE_COLOR);
		setSize(250, 100);
		setResizable(false);

		JPanel content = new JPanel(new BorderLayout());
		content.setBorder(new EmptyBorder(15, 15, 15, 15));
		content.putClientProperty(SubstanceSynapse.COLORIZATION_FACTOR, 1.0);

		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(layerManager.getLayerNames().toArray(new String[]{}));

		comboBox = new JComboBox<>(model);
		comboBox.setEditable(true);

		comboBox.addItemListener(ie -> {
			if (ie.getStateChange() == ItemEvent.SELECTED)
			{
				String value = (String) comboBox.getSelectedItem();
				consumer.accept(value, infoBox);
			}
		});

		comboBox.setSelectedIndex(Math.max(model.getIndexOf(layerManager.getLayerName(infoBox)), 0));
		content.add(comboBox);
		setContentPane(content);
		pack();
		setLocationRelativeTo(parent);

		setVisible(true);
	}
}