package dao;

import model.User;

import java.util.List;

public interface RecordSetDAO<T> {

    public void create(T _model);

    public T read(long id);

    public boolean update(T _model);

    public void delete(T _model);

    public List<T> readAll(String _sortField, int _sortDirection,
                           int _startIndex, int _recordCount);


    public List<T> readByFields(List<String> _fkFieldName,
                                List<Object> _fkFieldVal, String _sortField, int _sortDirection,
                                int _startIndex, int _recordCount);

    public List<T> readByFields(List<String> _fkFieldName,
                                List<Object> _fkFieldVal, String _sortField, int _sortDirection);



    public List<User> fetchUserbyNationalId(String nationalId);


}
