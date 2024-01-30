/*
 * MIT License
 *
 * Copyright (c) 2024 Masterplan AG
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package ch.masterplan.skicheck.ui.util;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class Notifier {

	public void notifySuccess(String messageHtml) {
		Notification notification = new Notification();
		notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
		notification.setPosition(Notification.Position.MIDDLE);

		HorizontalLayout layout = new HorizontalLayout(VaadinIcon.CHECK_CIRCLE.create(), new Div(messageHtml), generateCloseButton(notification));
		layout.setAlignItems(FlexComponent.Alignment.CENTER);

		notification.add(layout);
		notification.open();
	}

	public void notifySuccessAsTray(String messageHtml) {
		Notification notification = new Notification();
		notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
		notification.setDuration(4000);
		notification.setPosition(Notification.Position.BOTTOM_START);

		HorizontalLayout layout = new HorizontalLayout(VaadinIcon.CHECK_CIRCLE.create(), new Div(messageHtml), generateCloseButton(notification));
		layout.setAlignItems(FlexComponent.Alignment.CENTER);

		notification.add(layout);
		notification.open();
	}

	public void notifyWarning(String messageHtml) {
		Notification notification = new Notification();
		notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
		notification.setPosition(Notification.Position.MIDDLE);
		HorizontalLayout layout = new HorizontalLayout(VaadinIcon.WARNING.create(), new Div(messageHtml), generateCloseButton(notification));
		layout.setAlignItems(FlexComponent.Alignment.CENTER);

		notification.add(layout);
		notification.open();
	}

	public void notifyError(String messageHtml) {
		Notification notification = new Notification();
		notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
		notification.setPosition(Notification.Position.MIDDLE);
		HorizontalLayout layout = new HorizontalLayout(VaadinIcon.WARNING.create(), new Div(messageHtml), generateCloseButton(notification));
		layout.setAlignItems(FlexComponent.Alignment.CENTER);

		notification.add(layout);
		notification.open();
	}

	private Button generateCloseButton(Notification notification) {
		Button closeButton = new Button(new Icon("lumo", "cross"));
		closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
		closeButton.setAriaLabel("Close");
		closeButton.addClickListener(event -> notification.close());
		return closeButton;
	}
}
