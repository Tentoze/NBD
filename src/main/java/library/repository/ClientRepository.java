package library.repository;

import jakarta.persistence.EntityManager;
import library.model.Book;
import library.model.Client;

import java.util.List;

public class ClientRepository extends JpaRepositoryImpl<Client>{
    public ClientRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public Client findByPersonalID(String personalID){
        return entityManager.createNamedQuery("Client.findByPersonalID",Client.class).setParameter("personalID",personalID).getSingleResult();
    }

    public List<Client> findAll(){
        return entityManager.createNamedQuery("Client.findAll",Client.class).getResultList();
    }

    public void updateIsArchiveByPersonalID(String personalID, Boolean isArchive){
        entityManager.createNamedQuery("Client.findByPersonalID",Client.class).setParameter("personalID",personalID).setParameter("isArchive",isArchive);
    }

    public void updateDebtByPersonalID(String personalID,Float debt){
        entityManager.createNamedQuery("Client.updateDebtByPersonalID",Client.class).setParameter("personalID",personalID).setParameter("debt",debt);
    }
}
