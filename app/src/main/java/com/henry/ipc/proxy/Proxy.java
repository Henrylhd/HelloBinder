package com.henry.ipc.proxy;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.henry.ipc.Book;
import com.henry.ipc.server.BookManager;
import com.henry.ipc.server.Stub;

import java.util.List;

/**
 * Created by henry on 2018/4/12.
 */

public class Proxy implements BookManager {
    private static final String DESCRIPTOR = "com.henry.ipc.server.BookManager";
    private IBinder remote;

    public Proxy(IBinder remote) {
        this.remote = remote;
    }

    public String getInterfaceDescriptor() {
        return DESCRIPTOR;
    }

    @Override
    public List<Book> getBooks() throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        List<Book> result;
        try {
            data.writeInterfaceToken(DESCRIPTOR);
            remote.transact(Stub.TRANSAVTION_getBooks, data, reply, 0);
            reply.readException();
            result = reply.createTypedArrayList(Book.CREATOR);
        } finally {
            reply.recycle();
            data.recycle();
        }
        return result;
    }

    @Override
    public void addBook(Book book) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInterfaceToken(DESCRIPTOR);
            if (book !=null){
                data.writeInt(1);
                book.writeToParcel(data,0);
            }else {
                data.writeInt(0);
            }
            remote.transact(Stub.TRANSAVTION_addBook,data,reply,0);
            reply.readException();
        }finally {
            reply.recycle();
            data.recycle();
        }
    }

    @Override
    public IBinder asBinder() {
        return remote;
    }
}
