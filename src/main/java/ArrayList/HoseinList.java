package ArrayList;

import java.util.Collection;

public interface HoseinList<T> extends Collection<T> {




    // index of an element !
    int indexOf(T t);

    //object migire mizare avale list !
    void insertFirst(T t);

    //object e ro migire mizare bad az t !!
    void insertAfter(T t, T e);

    //object t ro to akhare list insert mikone !
    void append(T t);

    //avalin object ro barmigardoone !
    T getFirst();

    // avalin object ro hazf mikone !
    T deleteFirst();

    // object e bad az e ro hazf mikone !
    T deleteAfter(T e);

    //akharin object ro hazf mikone !!
    T deletelast();

    // index migire bad objecti ke to on indexe ro hazf mikone !!!!
    T deleteIndexOf(Integer i);

    //index migire object pas mide !!!
    T getElement(Integer i);


}
