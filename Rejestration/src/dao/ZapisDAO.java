package dao;

import java.util.List;

public interface ZapisDAO {
    void confirm(Integer id);
    void reject(Integer id);

    List findAllConfirmedZapis();
    List findAllNotConfirmedZapis();
}
