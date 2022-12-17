package by.korziuk.check_app.factory;

public class CustomerCheckFactory implements CheckFactory {

    /**
     * Create Customer Check Instance
     * @return new Customer Check
     */
    @Override
    public Check createCheck() {
        return new CustomerCheck();
    }
}
