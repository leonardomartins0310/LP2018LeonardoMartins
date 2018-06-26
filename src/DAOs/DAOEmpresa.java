package DAOs;


import Entidades.Empresa;
import daos.DAOGenerico;
import static daos.DAOGenerico.em;
import java.util.ArrayList;
import java.util.List;

public class DAOEmpresa extends DAOGenerico<Empresa> {

    public DAOEmpresa() {
        super(Empresa.class);
    }

    public int autoIdEmpresa() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.idEmpresa) FROM Empresa e ").getSingleResult();
        if (a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }

    public List<Empresa> listByNome(String nome) {
        return em.createQuery("SELECT e FROM Empresa e WHERE e.nomeEmpresa LIKE :nome").setParameter("nome", "%" + nome + "%").getResultList();
    }

    public List<Empresa> listById(int id) {
        return em.createQuery("SELECT e FROM Empresa e WHERE e.idEmpresa = :id").setParameter("id", id).getResultList();
    }

    public List<Empresa> listInOrderNome() {
        return em.createQuery("SELECT e FROM Empresa e ORDER BY e.nomeEmpresa").getResultList();
    }

    public List<Empresa> listInOrderId() {
        return em.createQuery("SELECT e FROM Empresa e ORDER BY e.idEmpresa").getResultList();
    }

    public List<String> listInOrderNomeStrings(String qualOrdem) {
        List<Empresa> lf;
        if (qualOrdem.equals("id")) {
            lf = listInOrderId();
        } else {
            lf = listInOrderNome();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getIdEmpresa() + "-" + lf.get(i).getNomeEmpresa());
        }
        return ls;
    }
}
