package beans;

public abstract class Tables {

	protected abstract int enregistrerBdd();

	protected abstract void modifierBdd();

	protected abstract void supprimerBdd();

	protected abstract int maxId();

}
