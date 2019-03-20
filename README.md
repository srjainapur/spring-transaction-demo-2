Here we will see a basic example of spring transaction management using spring boot. Also, we will see how to use @Transactional annotation.

Different ways of the transaction management-
    Programmatic transaction management.
    Declarative transaction management.

Programmatic transaction management – Here we need to write some extra code for transaction management. When we say some extra code what does it mean? We need to take care of –
    Creating Transaction reference
    Begin transaction
    Commit or rollback of the transaction

	Transaction transactionRef = entityManager.getTransaction()                  
	try {  
		transactionRef.begin();                   
		// business logic                   
		transactionRef .commit();  
	} catch(Exception e) {                     
		transactionRef.rollback();  
		e.printStackTrace();                 
	}
	
Declarative transaction management – No need to write extra code for getting a transaction, we can use annotations or XML-based approach to manage the transactions and we can avoid unnecessary code. If we use annotation based approach we can use @Transactional and if we use the XML-based approach we need to configure DataSourceTransactionManager or any other transaction manager in XML as a bean. In this article and the next upcoming article, we will see the annotation based approach.
Sample code for annotation based declarative transaction management –

@Transactional(readOnly=true, propagation = Propagation.REQUIRES_NEW)
public Book findOneString objectId) {
	return repository.findOne(objectId);
}
