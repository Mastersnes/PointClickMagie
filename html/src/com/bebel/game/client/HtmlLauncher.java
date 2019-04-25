package com.bebel.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.backends.gwt.preloader.Preloader;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.TimeUtils;
import com.bebel.game.LaunchGame;
import com.bebel.game.utils.Constantes;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;

public class HtmlLauncher extends GwtApplication {

        // USE THIS CODE FOR A FIXED SIZE APPLICATION
        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(1024, 768);
        }
        // END CODE FOR FIXED SIZE APPLICATION

        // UNCOMMENT THIS CODE FOR A RESIZABLE APPLICATION
        // PADDING is to avoid scrolling in iframes, set to 20 if you have problems
        // private static final int PADDING = 0;
        // private GwtApplicationConfiguration cfg;
        //
        // @Override
        // public GwtApplicationConfiguration getConfig() {
        //     int w = Window.getClientWidth() - PADDING;
        //     int h = Window.getClientHeight() - PADDING;
        //     cfg = new GwtApplicationConfiguration(w, h);
        //     Window.enableScrolling(false);
        //     Window.setMargin("0");
        //     Window.addResizeHandler(new ResizeListener());
        //     cfg.preferFlash = false;
        //     return cfg;
        // }
        //
        // class ResizeListener implements ResizeHandler {
        //     @Override
        //     public void onResize(ResizeEvent event) {
        //         int width = event.getWidth() - PADDING;
        //         int height = event.getHeight() - PADDING;
        //         getRootPanel().setWidth("" + width + "px");
        //         getRootPanel().setHeight("" + height + "px");
        //         getApplicationListener().resize(width, height);
        //         Gdx.graphics.setWindowedMode(width, height);
        //     }
        // }
        // END OF CODE FOR RESIZABLE APPLICATION

        @Override
        public ApplicationListener createApplicationListener () {
                return new LaunchGame();
        }

        long loadStart = TimeUtils.nanoTime();
        @Override
        public Preloader.PreloaderCallback getPreloaderCallback() {
                final Canvas canvas = Canvas.createIfSupported();
                canvas.setWidth("" + (int)(getConfig().width * 0.7f) + "px");
                canvas.setHeight("70px");
                getRootPanel().add(canvas);
                final Context2d context = canvas.getContext2d();
                context.setTextAlign(Context2d.TextAlign.CENTER);
                context.setTextBaseline(Context2d.TextBaseline.MIDDLE);
                context.setFont("18pt Calibri");

                Constantes.LOCATION_URL = GWT.getHostPageBaseURL();

                return new Preloader.PreloaderCallback() {
                        @Override
                        public void update(Preloader.PreloaderState state) {
                                String color = Pixmap.make(30, 30, 30, 1);
                                context.setFillStyle(color);
                                context.setStrokeStyle(color);
                                context.fillRect(0, 0, 300, 70);
                                color = Pixmap.make(200, 200, 200, (((TimeUtils.nanoTime() - loadStart) % 1000000000) / 1000000000f));
                                context.setFillStyle(color);
                                context.setStrokeStyle(color);
                                context.fillRect(0, 0, 300 * state.getProgress(), 70);

                                context.setFillStyle(Pixmap.make(50, 50, 50, 1));
                                context.fillText("loading", 300 / 2, 70 / 2);
                        }

                        @Override
                        public void error(String file) {
                                System.out.println("erreur pour le fichier :" + file);
                        }
                };
        }
}