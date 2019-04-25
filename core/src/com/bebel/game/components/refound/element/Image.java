package com.bebel.game.components.refound.element;

import com.bebel.game.components.interfaces.Refreshable;
import com.bebel.game.components.refound.abstrait.AbstractComponent;

public class Image extends AbstractComponent implements Refreshable {
    public Image() {
        super();
    }

    public void init(final String key) {
        setName(key);
        refresh();
    }

    @Override
    public void refresh() {
        setRegion(manager.getTexture(getName()));
        setSize(getRegionWidth(), getRegionHeight());
    }

    @Override
    protected void actComponent(float delta) {
    }

    @Override
    public void makeComponentEvents() {
    }

    @Override
    public void resetComponent() {
        setTexture(null);
    }
}
