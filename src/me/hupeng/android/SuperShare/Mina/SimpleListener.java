package me.hupeng.android.SuperShare.Mina;

import org.apache.mina.core.session.IoSession;

/**
 * Created by HUPENG on 2016/9/27.
 */
public interface SimpleListener {
    public void onReceive(Object obj, IoSession ioSession);
}
