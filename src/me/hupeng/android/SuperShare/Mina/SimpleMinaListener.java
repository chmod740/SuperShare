package me.hupeng.android.SuperShare.Mina;

import org.apache.mina.core.session.IoSession;

/**
 * Created by HUPENG on 2016/9/27.
 */
public interface SimpleMinaListener {
    public void onReceive(Object obj, IoSession ioSession);
    public void onLine(IoSession session);
    public void offLine(IoSession session);
}
