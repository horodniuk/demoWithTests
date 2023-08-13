CREATE OR REPLACE FUNCTION check_employee_email()
    RETURNS TRIGGER
AS
$$
BEGIN
    IF NEW.email IS NOT NULL AND NEW.email NOT LIKE '%@gmail.com' THEN
        RAISE EXCEPTION 'Email does not end with "@gmail.com": %', NEW.email;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_employee_email_gmail
    BEFORE INSERT OR UPDATE
    ON users
    FOR EACH ROW
EXECUTE FUNCTION check_employee_email();