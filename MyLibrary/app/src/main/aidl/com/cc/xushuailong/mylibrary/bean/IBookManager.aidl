// IBookManager.aidl
package com.cc.xushuailong.mylibrary.bean;

import com.cc.xushuailong.mylibrary.bean.Book;

// Declare any non-default types here with import statements

interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
    //获得所有的Book对象
    List<Book> getBookList();
    //添加Book对象
    void addBook(in Book book);
}
