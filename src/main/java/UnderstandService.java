import com.scitools.understand.Database;
import com.scitools.understand.Understand;
import com.scitools.understand.UnderstandException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Marco on 24/09/16.
 */
public class UnderstandService
{
    private Map<String,Database> dbs;

    private UnderstandService service = null;

    public UnderstandService()
    {
        if(service == null)
        {
            service = new UnderstandService();
            dbs = new HashMap<>();
        }
    }

    public void addDB(String name) throws UnderstandException
    {
        String path = UnderstandService.class.getClassLoader().getResource(name + ".udb").toString();
        dbs.put(name, Understand.open(path));
    }

    public Database getDB(String name)
    {
        return  dbs.get(name);
    }

}
