package com.excilys.formation.gwt.client.slider.slides;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.excilys.formation.gwt.client.slider.shownotes.ElementSlideNotes;
import com.excilys.formation.gwt.client.slider.shownotes.WidgetSlideNotes;
import com.google.gwt.uibinder.client.UiBinder;

public abstract class Chapter implements Iterable<Presentable> {

    private static final String IMPL_SUFFIX = "Impl";
    private List<Presentable> slides;
    private final List<String> slideNames = new ArrayList<String>();
    private String holderName;

    public final Presentable getSlide(int slideIndex) {
        return ensureSlides().get(doCheckIndex(slideIndex));
    }

    public final Presentable getSlideOrNull(int slideIndex) {
        ensureSlides();
        if (slideIndex < 0) {
            return null;
        } else if (slideIndex >= slides.size()) {
            return null;
        }
        return slides.get(slideIndex);
    }

    public final int getSlideCount() {
        return ensureSlides().size();
    }

    @Override
    public final Iterator<Presentable> iterator() {
        return ensureSlides().iterator();
    }

    public final boolean isLastSlide(int slideIndex) {
        ensureSlides();
        return doCheckIndex(slideIndex) == slides.size() - 1;
    }

    public final boolean isFirstSlide(int slideIndex) {
        ensureSlides();
        return doCheckIndex(slideIndex) == 0;
    }

    /**
     * Unsafe: does not ensure slides
     */
    private final int doCheckIndex(int index) {
        if (index < 0) {
            index = 0;
        } else if (index >= slides.size()) {
            index = slides.size() - 1;
        }
        return index;
    }

    public final int checkIndex(int index) {
        ensureSlides();
        return doCheckIndex(index);
    }

    private List<Presentable> ensureSlides() {
        if (slides == null) {
            slides = new ArrayList<Presentable>();
            buildSlides();
            slides = Collections.unmodifiableList(slides);
        }
        return slides;
    }

    protected abstract void buildSlides();

    private String getUiBinderName(Object uiBinder) {
        String uiBinderClassSimpleName = ClassHelper.getSimpleName(uiBinder.getClass());
        String chapterClassSimpleName = ClassHelper.getSimpleName(getClass());

        String nameWithoutChapterClassName;
        if (uiBinderClassSimpleName.startsWith(chapterClassSimpleName)) {
            nameWithoutChapterClassName = uiBinderClassSimpleName.replaceFirst(chapterClassSimpleName, "");
        } else if (uiBinderClassSimpleName.startsWith(holderName)) {
            nameWithoutChapterClassName = uiBinderClassSimpleName.replaceFirst(holderName, "");
        } else {
            nameWithoutChapterClassName = uiBinderClassSimpleName;
        }
        if (nameWithoutChapterClassName.endsWith(IMPL_SUFFIX)) {
            int finalNameLength = nameWithoutChapterClassName.length() - IMPL_SUFFIX.length();
            return nameWithoutChapterClassName.substring(0, finalNameLength);
        } else {
            return nameWithoutChapterClassName;
        }

    }

    /**
     * To be used with GWT.create(XXX.class), provided that XXX.class extends
     * UiBinder<Element, ElementSlide>
     * 
     * @param uiBinder
     *            Must be a UiBinder<Element, ElementSlide>. The signature is
     *            Object because GWT.create is generic.
     */
    protected final void addSlide(UiBinder<?, ?> uiBinder) {
        addPresentable(new ElementSlide(uiBinder), getUiBinderName(uiBinder));
    }

    protected final void addNotesSlide(UiBinder<?, ?> uiBinder) {
        addPresentable(new ElementSlideNotes(uiBinder), getUiBinderName(uiBinder));
    }

    protected final void addWidgetNotesSlide(UiBinder<?, ?> uiBinder) {
        addPresentable(new WidgetSlideNotes(uiBinder), getUiBinderName(uiBinder));
    }

    /**
     * To be used with GWT.create(XXX.class), provided that XXX.class extends
     * UiBinder<Widget, WidgetSlide>
     * 
     * @param uiBinder
     *            Must be a UiBinder<Widget, WidgetSlide>. The signature is
     *            Object because GWT.create is generic.
     */
    protected final void addWidgetSlide(UiBinder<?, ?> uiBinder) {
        addPresentable(new WidgetSlide(uiBinder), getUiBinderName(uiBinder));
    }

    /**
     * To be used with GWT.create(XXX.class), provided that XXX.class extends
     * UiBinder<Widget, VisibleSlide>
     * 
     * @param uiBinder
     *            Must be a UiBinder<Widget, VisibleSlide>. The signature is
     *            Object because GWT.create is generic.
     */
    protected final void addVisibleSlide(UiBinder<?, ?> uiBinder) {
        addPresentable(new VisibleSlide(uiBinder), getUiBinderName(uiBinder));
    }

    protected final void addPresentable(Presentable presentable) {
        addPresentable(presentable, ClassHelper.getSimpleName(presentable.getClass()));
    }

    protected final void addPresentable(Presentable presentable, String slideName) {
        slides.add(presentable);
        String realSlideName;
        slideName = slideName.replace("_", "");

        if (slideNames.contains(slideName)) {
            int i = 1;
            do {
                i++;
                realSlideName = slideName + i;
            } while (slideNames.contains(realSlideName));
        } else {
            realSlideName = slideName;
        }
        slideNames.add(realSlideName);
    }

    /**
     * May be overriden if you want to change the chapter name
     */
    public String getName() {
        return ClassHelper.getSimpleName(getClass());
    }

    /**
     * Returns the slide index. The
     */
    public final int getSlideIndex(String slideName) {
        ensureSlides();
        return doCheckIndex(slideNames.indexOf(slideName));
    }

    public final String getSlideName(int slideIndex) {
        ensureSlides();
        return slideNames.get(doCheckIndex(slideIndex));
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }
}
