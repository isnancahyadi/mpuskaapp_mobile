package com.arcmedtek.mpuskaapp_mobile.model;

public class VariantProgramModel {
    int _img;
    String _color, _programName;

    public VariantProgramModel() {
    }

    public VariantProgramModel(int _img, String _color, String _programName) {
        this._img = _img;
        this._color = _color;
        this._programName = _programName;
    }

    public int get_img() {
        return _img;
    }

    public String get_color() {
        return _color;
    }

    public String get_programName() {
        return _programName;
    }
}
