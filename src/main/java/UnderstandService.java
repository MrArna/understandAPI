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


    public UnderstandService()
    {
        if(dbs == null)
        {
            dbs = new HashMap<>();
        }
    }

    public void addDB(String name) throws UnderstandException
    {
        String path = this.getClass().getResource(name + ".udb").toString();
        System.out.println(path);
        dbs.put(name, Understand.open("/Users/Marco/Understand/Maruora.udb"));
    }

    public Database getDB(String name)
    {
        return  dbs.get(name);
    }

}
