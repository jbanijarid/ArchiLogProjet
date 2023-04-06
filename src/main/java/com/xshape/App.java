package com.xshape;

import com.xshape.modele.XShape;
import com.xshape.vue.awt.AwtApp;
import com.xshape.vue.fx.FxApp;

public class App {

    public static void main(String[] args) {
        XShape appAwt = new AwtApp();
        appAwt.run();
        XShape appFx = new FxApp();
        appFx.run();

    }

}