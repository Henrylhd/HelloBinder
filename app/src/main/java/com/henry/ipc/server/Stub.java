package com.henry.ipc.server;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.henry.ipc.Book;
import com.henry.ipc.proxy.Proxy;

import java.util.List;

/**
 * Created by henry on 2018/4/12.
 */
/*
* https://mp.weixin.qq.com/s?__biz=MzAxMTg2MjA2OA==&mid=2649842188&idx=1&sn=e7023235f76d48fcfe330435bd882d0c&chksm=83bf6b57b4c8e24115f48476625e6be5a732b64f5593351b8ba601c6e9327d48c265105304c5&mpshare=1&scene=1&srcid=0412fYvMb0i4QXACLTxJ4n8U&key=5af0cbb07289e7211d435ae9cd110d07fde7f75ad0c7ea5eec7466a4a61cd426a9f9bb25b15bd0599b45c7a23757cc89a40f24544bbb0cade57d5340c905563d636795540e19759de9c3e747a4f1351d&ascene=0&uin=MTE5NDc1NjQwOA%3D%3D&devicetype=iMac+MacBookPro11%2C4+OSX+OSX+10.12+build(16A323)&version=11020012&lang=zh_CN&pass_ticket=5xfW%2FDcEDHg3FUaHZj8iK%2BTpPDmxlbz%2Bo0wZW8reNq594G%2F%2Fj4YoNmxD1pn4L7NO*/
public abstract class Stub extends Binder implements BookManager {
    private static final String DESCRIPTOR = "com.henry.ipc.server.BookManager";

    public Stub() {
        this.attachInterface(this, DESCRIPTOR);
    }

    public static BookManager asInterface(IBinder binder) {
        if (binder == null)
            return null;
        IInterface iInterface = binder.queryLocalInterface(DESCRIPTOR);
        if (iInterface != null && iInterface instanceof BookManager)
            return (BookManager) iInterface;
        return new Proxy(binder);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case INTERFACE_TRANSACTION:
                reply.writeString(DESCRIPTOR);
                return true;
            case TRANSAVTION_getBooks:
                data.enforceInterface(DESCRIPTOR);
                List<Book> result = this.getBooks();
                reply.writeNoException();
                reply.writeTypedList(result);
                return true;
            case TRANSAVTION_addBook:
                data.enforceInterface(DESCRIPTOR);
                Book arg0 = null;
                if (data.readInt() != 0) {
                    arg0 = Book.CREATOR.createFromParcel(data);
                }
                this.addBook(arg0);
                reply.writeNoException();
                return true;

        }
        return super.onTransact(code, data, reply, flags);
    }

    public static final int TRANSAVTION_getBooks = IBinder.FIRST_CALL_TRANSACTION;
    public static final int TRANSAVTION_addBook = IBinder.FIRST_CALL_TRANSACTION + 1;
}
