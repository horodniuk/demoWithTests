CREATE OR REPLACE FUNCTION audit_function()
    RETURNS TRIGGER
AS
$$
BEGIN
    INSERT INTO audit(id, table_name, date)
    values (new.id, tg_table_name, now());
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER audit_employee_trigger
    AFTER UPDATE OR INSERT
    ON users
    FOR EACH ROW
EXECUTE FUNCTION audit_function();