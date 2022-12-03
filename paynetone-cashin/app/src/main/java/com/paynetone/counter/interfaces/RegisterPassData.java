package com.paynetone.counter.interfaces;

import com.paynetone.counter.model.RegisterPassDataModel;

public interface RegisterPassData {
    void saveData(RegisterPassDataModel dataModel);

    RegisterPassDataModel getData();
}
