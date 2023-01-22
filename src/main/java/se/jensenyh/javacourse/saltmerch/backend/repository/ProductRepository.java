package se.jensenyh.javacourse.saltmerch.backend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.jensenyh.javacourse.saltmerch.backend.model.ColorVariant;
import se.jensenyh.javacourse.saltmerch.backend.model.Product;
import se.jensenyh.javacourse.saltmerch.backend.model.SizeContainer;

@Repository
public class ProductRepository
{
    @Autowired
    JdbcTemplate jdbcTemplate;
    // NOTE: LEAVE THIS RECORD AS IT IS!
    private record VariantWImages(int id, String colorName, String imagesCsv) {}
    
    
    
    /** Only calls selectAll(String category) with a null category;
     * Useful for reading ALL products, regardless of category. */
    public List<Product> selectAll()
    {
        return selectAll(null);
    }
    
    // todo: this method needs you to write its SQL query
    /** Reads all rows from the products table and returns them as a List of Products. */



    public List<Product> selectAll(String category)
    {
        // todo: write an SQL query that only selects all rows from the products table
        String sql = "SELECT * FROM products";// <<<< todo: WRITE SQL QUERY HERE
        
        
        
        // NOTE: leave this line as it is!
        if (category != null) sql += " WHERE category = (:category)";
        

        
        // todo: create a RowMapper for the Product class,
        //  using the constructor that takes id, category, title, description, and previewImage
        // NOTE: have in mind that the column name that corresponds to previewImage is preview_image
        RowMapper<Product> rm = (rs, rowNum) -> new Product(// <<<< todo: CREATE RowMapper HERE

                rs.getInt("id"),
                rs.getString("category"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("preview_image"));


        // NOTE: leave the rest as it is!
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("category", category);
        return new NamedParameterJdbcTemplate(jdbcTemplate).query(sql, paramMap, rm);
    }
    
    /** Only calls selectAll(String category) with a specific category.
     * Useful for reading all products of a specific category. */
    public List<Product> selectAllOfCategory(String category)
    {
        return selectAll(category);
    }
    
    // NOTE: NO NEED TO MODIFY THIS METHOD!
    /** Inserts a new product in the database, together with its
     *      variants, including images and sizes. It takes a
     *      Product parameter containing everything. */
    public Product insertProductAndProps(Product prod, String category)
    {
        var sql = """
                INSERT INTO products (category, title, description, preview_image)
                VALUES (?, ?, ?, ?) RETURNING id;""";
        RowMapper<Integer> rm = (rs, rowNum) -> rs.getInt("id");
        List<Integer> pids = jdbcTemplate.query(sql, rm, category, prod.title,
                                                prod.description, prod.previewImage);
        int pid = pids.size() > 0 ? pids.get(0) : -1;
        
        Product newProd = null;
        if (pid > -1)
        {
            newProd = new Product();
            newProd.id = pid;
            newProd.category = category;
            newProd.title = prod.title;
            newProd.description = prod.description;
            newProd.previewImage = prod.previewImage;
            
            for (ColorVariant v : prod.colorVariants)
            {
                ColorVariant newv = new ColorVariant();
                RowMapper<Integer> rmv = (rs, rowNum) -> rs.getInt("id");
                var sqlv = """
                        INSERT INTO variants (color_name, product_id) VALUES (?, ?) RETURNING id;""";
                List<Integer> vids = jdbcTemplate.query(sqlv, rmv, v.colorName, pid);
                int vid = vids.size() > 0 ? vids.get(0) : -1;
                
                if (vid > -1)
                {
                    newv.colorName = v.colorName;
                    
                    for (String url : v.images)
                    {
                        var sqli = """
                                INSERT INTO images (url, variant_id) VALUES (?, ?);""";
                        int ires = jdbcTemplate.update(sqli, url, vid);
                        if (ires == 1)
                            newv.images.add(url);
                    }
                    for (SizeContainer s : v.sizes)
                    {
                        var sqls = """
                                INSERT INTO sizes (size, stock, variant_id) VALUES (?, ?, ?);""";
                        int sres = jdbcTemplate.update(sqls, s.size, s.stock, vid);
                        if (sres == 1)
                            newv.sizes.add(s);
                    }
                    newProd.colorVariants.add(newv);
                }
            }
        }
        return newProd;
    }
    
    // NOTE: NO NEED TO MODIFY THIS METHOD!
    /** Updates a specific row in the products table
     *  (only its title, description, and preview_image). */
    public int updateProductMeta(int id, Product prod)
    {
        Product oldProd = getProductBase(id);
        if (oldProd == null)
            return -9;
        else
        {
            String title = prod.title != null && !prod.title.isEmpty()
                    ? prod.title : oldProd.title;
            String desc = prod.description != null && !prod.description.isEmpty()
                    ? prod.description : oldProd.description;
            String img = prod.previewImage != null && !prod.previewImage.isEmpty()
                    ? prod.previewImage : oldProd.previewImage;
            var sql = """
                    UPDATE products
                    SET title = ?, description = ?, preview_image = ?
                    WHERE id = ?;""";
            return jdbcTemplate.update(sql, title, desc, img, id);
        }
    }
    
    // todo: this method needs you to write its SQL query and execute it
    // NOTE: optional
    /** Deletes a specific row from the products table. */
    public int deleteProduct(int id)
    {
        // todo: write the SQL query for deleting a single product
        var sql = """
                """;// <<<< todo: WRITE SQL QUERY HERE
        
        
        // todo: execute the query while also passing the id as a parameter
        return -1000;// <<<< todo: call jdbcTemplate method here
    }
    
    // NOTE: NO NEED TO MODIFY THIS METHOD!
    /** Reads rows from products, variants, images, and sizes,
     * constructs ONE Product object from them, and returns it. */
    public Product getEntireProduct(int productId)
    {
        Product product = getProductBase(productId);
        if (product == null)
        {
            System.out.println("Product wasn't fetched from the db");
            return null;
        }
        List<VariantWImages> variantsWImages = getVariantsAndImages(productId);
        System.out.println("variantsWImages = " + variantsWImages);
        for (VariantWImages variantWImages : variantsWImages)
        {
            ColorVariant colorVariant = new ColorVariant();
            colorVariant.colorName = variantWImages.colorName;
            colorVariant.sizes = getVariantSizes(variantWImages.id);
            try
            {
                colorVariant.setImagesFromCSV(variantWImages.imagesCsv);
            }
            catch (Exception e)
            {
                colorVariant.images = new ArrayList<>();
                System.out.println("Exception parsing images from csv; see stack trace");
                e.printStackTrace();
            }
            product.colorVariants.add(colorVariant);
        }
        
        return product;
    }
    
    // NOTE: NO NEED TO MODIFY THIS METHOD!
    /** Utility method used in getEntireProduct().
     *      Reads rows from variants and images tables. */
    public List<VariantWImages> getVariantsAndImages(int productId)
    {
        var sql = """
                SELECT v.id AS v_id, v.color_name,
                	STRING_AGG(url, ',') images
                FROM variants AS v
                LEFT OUTER JOIN images AS i1
                ON v.id = i1.variant_id
                WHERE v.product_id = ?
                GROUP BY v_id, v.color_name
                """;
        RowMapper<VariantWImages> rm = (rs, rowNum) -> new VariantWImages(
                rs.getInt("v_id"),
                rs.getString("color_name"),
                rs.getString("images"));
        return jdbcTemplate.query(sql, rm, productId);
    }
    
    // NOTE: NO NEED TO MODIFY THIS METHOD!
    /** Utility method used in getEntireProduct().
     *      Reads a row from sizes table. */
    public List<SizeContainer> getVariantSizes(int variantId)
    {
        var sql = """
                SELECT size, stock
                FROM variants v
                INNER JOIN sizes AS s
                ON v.id = s.variant_id
                WHERE v.id = ?
                """;
        RowMapper<SizeContainer> rm = (rs, rowNum) -> new SizeContainer(
                rs.getString("size"),
                rs.getInt("stock"));
        return jdbcTemplate.query(sql, rm, variantId);
    }
    
    // NOTE: NO NEED TO MODIFY THIS METHOD!
    /** Inserts rows into variants, images, and sizes,
     * thus creating a new variant for a specific product. */
    public ColorVariant addVariant(int productId, ColorVariant colorVariant)
    {
        ColorVariant newv = new ColorVariant();
        RowMapper<Integer> rmv = (rs, rowNum) -> rs.getInt("id");
        var sqlv = """
                INSERT INTO variants (color_name, product_id) VALUES (?, ?) RETURNING id;""";
        List<Integer> vids = jdbcTemplate.query(sqlv, rmv, colorVariant.colorName, productId);
        int vid = vids.size() > 0 ? vids.get(0) : -1;
        
        if (vid > -1)
        {
            newv.colorName = colorVariant.colorName;
            
            for (String url : colorVariant.images)
            {
                var sqli = "INSERT INTO images (url, variant_id) VALUES (?, ?);";
                int ires = jdbcTemplate.update(sqli, url, vid);
                if (ires == 1)
                    newv.images.add(url);
            }
            for (SizeContainer s : colorVariant.sizes)
            {
                var sqls = "INSERT INTO sizes (size, stock, variant_id) VALUES (?, ?, ?);";
                int sres = jdbcTemplate.update(sqls, s.size, s.stock, vid);
                if (sres == 1)
                    newv.sizes.add(s);
            }
        }
        return newv;
    }
    
    // todo: this method needs you to write its SQL query
    // NOTE: optional
    /** Delete a specific row from variants. */
    public int deleteVariant(int productId, String color)
    {
        // todo: write the SQL query for deleting a variant
        //  with specific product_id and color_name
        var sql = "";// <<<< todo: WRITE SQL QUERY HERE
    
    
        // todo: execute the query while also passing the id as a parameter
        return -1000;// <<<< todo: call jdbcTemplate method here
    }
    
    // NOTE: the endpoint that's supposed to use this method is OPTIONAL!
    // NOTE: NO NEED TO MODIFY THIS METHOD!
    /** Restocks a specific product variant, i.e.
     *      adds a certain number to its stock. */
    public int restockSize(int productId, String size, String color, int qty)
    {
        var sql = """
                UPDATE sizes SET stock = stock + ?
                WHERE id = (
                    SELECT s.id FROM sizes AS s
                    INNER JOIN variants AS v
                    ON v.id = s.variant_id
                    WHERE product_id = ? AND size = ? AND color_name = ?);""";
        return jdbcTemplate.update(sql, qty, productId, size, color);
    }
    
    // NOTE: NO NEED TO MODIFY THIS METHOD!
    /** Utility function used in other methods.
     *      Only reads a product's metadata. */
    private Product getProductBase(int productId)
    {
        RowMapper<Product> rm = (rs, rowNum) -> new Product(
                rs.getInt("id"),
                rs.getString("category"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("preview_image"));
        var sql = "SELECT * FROM products WHERE id = ?";
        List<Product> products = jdbcTemplate.query(sql, rm, productId);
        return products.size() > 0 ? products.get(0) : null;
    }
}
