package library.managers;

import library.model.Adult;
import library.model.Child;
import library.model.Client;
import library.repository.ClientRepository;

import java.util.List;

public class ClientManager {
    private ClientRepository clientRepository;

    public ClientManager(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client getClient(String personalID) {
        return clientRepository.findByPersonalID(personalID);
    }

    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    public void registerClient(String firstname, String lastname, String personalID, int age) {
        if (age < 18) {
            clientRepository.add(new Child(firstname, lastname, personalID, age));
        } else {
            clientRepository.add(new Adult(firstname, lastname, personalID, age));
        }
    }

    public void unregisterClient(String personalID){
        clientRepository.updateIsArchiveByPersonalID(personalID,true); // dodac warunek czy oddal wszystkie ksiazki
        // i splacil debet
    }
}
