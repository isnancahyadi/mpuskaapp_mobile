package com.arcmedtek.mpuskaapp_mobile.config;

public interface OnEditTextChanged {
    void onTextChanged(int position, String charSeq);
    void beforeTextChanged(int position, String charSeq);
}
