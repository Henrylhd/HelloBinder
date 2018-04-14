package com.henry.ipc.server;

import android.os.IInterface;
import android.os.RemoteException;

import com.henry.ipc.Book;

import java.util.List;

/**
 * Created by henry on 2018/4/12.
 */

//这个类用来定义服务端RemoteService具备什么样的能力
public interface BookManager extends IInterface {
    List<Book> getBooks() throws RemoteException;
    void addBook(Book book) throws RemoteException;
}
