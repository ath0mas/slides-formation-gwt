package com.excilys.formation.gwt.client.formation.environnement_dev;

import com.excilys.formation.gwt.client.formation.FormationGwt.Plan;
import com.excilys.formation.gwt.client.slider.slides.Chapter;
import com.excilys.formation.gwt.client.slider.slides.ElementSlide;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;

public class EnvironnementDev extends Chapter {

    @UiTemplate("Titre.ui.xml")
    interface Titre extends UiBinder<Element, ElementSlide> {
        Titre binder = GWT.create(Titre.class);
    }

    @UiTemplate("GWTDesigner.ui.xml")
    interface GWTDesigner extends UiBinder<Element, ElementSlide> {
        GWTDesigner binder = GWT.create(GWTDesigner.class);
    }

    @UiTemplate("SpeedTracer.ui.xml")
    interface SpeedTracer extends UiBinder<Element, ElementSlide> {
        SpeedTracer binder = GWT.create(SpeedTracer.class);
    }

    @Override
    protected void buildSlides() {
        addSlide(Titre.binder);
        addSlide(GWTDesigner.binder);
        addSlide(SpeedTracer.binder);
        addSlide(Plan.binder);
    }

}
