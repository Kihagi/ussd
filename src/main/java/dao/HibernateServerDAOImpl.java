package dao;

import model.Common;
import model.User;
import org.hibernate.*;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HibernateServerDAOImpl<T extends Common> extends HibernateWrapper implements RecordSetDAO<T> {

    protected Class<T> tClass = null;

    public HibernateServerDAOImpl(Class<T> _tClass) {
        tClass = _tClass;
    }

    public void create(T _model) {
        Session session = sessionFactory.getCurrentSession();
        System.out.println("Session is null? " + session.toString());
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            long __recID = (Long) session.save(_model);
            _model.setId(__recID);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    public boolean update(T _model) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(_model);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public T read(long id) {
        T __t = null;
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            __t = (T) ((session.get(tClass, id)));
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        return __t;
    }

    public void delete(T _model) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(_model);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    public List<T> readAll(String _sortField, int _sortDirection,
                           int _startIndex, int _recordCount) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        List<T> __records = new ArrayList<T>();
        try {
            tx = session.beginTransaction();

            Criteria crit = session.createCriteria(tClass);
            if (_sortField.length() > 0) {
                if (_sortDirection == 0) {
                    crit = crit.addOrder(Order.asc(_sortField));
                }// end if
                else {
                    crit = crit.addOrder(Order.desc(_sortField));
                }// end else
            }
            __records = crit.setFirstResult(_startIndex)
                    .setMaxResults(_recordCount).list();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        return __records;
    }

    public List<T> readByFields(List<String> _fkFieldName,
                                List<Object> _fkFieldVal_, String _sortField, int _sortDirection,
                                int _startIndex, int _recordCount) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        List<T> __records = new ArrayList<T>();
        try {
            tx = session.beginTransaction();

            Criteria crit = session.createCriteria(tClass);
            Criterion fkCriterion[] = new Criterion[_fkFieldName.size()];
            for (int i = 0; i < _fkFieldName.size(); ++i) {
                String __fieldNameIndex = _fkFieldName.get(i);
                Object __fkFieldValIndex = _fkFieldVal_.get(i);
                fkCriterion[i] = Restrictions.eq(__fieldNameIndex, __fkFieldValIndex);
            }
            Conjunction __conjunction = Restrictions.and(fkCriterion);
            crit.add(__conjunction);

            if (_sortField.length() > 0) {
                if (_sortDirection == 0) {
                    crit = crit.addOrder(Order.asc(_sortField));
                }// end if
                else {
                    crit = crit.addOrder(Order.desc(_sortField));
                }// end else
            }
            __records = crit.setFirstResult(_startIndex)
                    .setMaxResults(_recordCount).list();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        return __records;
    }

    public List<T> readByFields(List<String> _fkFieldName,
                                List<Object> _fkFieldVal, String _sortField, int _sortDirection) {
        return readByFields(_fkFieldName, _fkFieldVal, _sortField, _sortDirection, 0, 100);
    }

    public List<User> fetchUserbyNationalId(String nationalId) {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;

        List<User> __records = new ArrayList<User>();

        try {
            tx = session.beginTransaction();
            String sql = "select user from User user left join fetch user.customers WHERE user.id = :org_id";


            tx.commit();
        } catch (HibernateException | ClassCastException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        return __records;
    }

}
