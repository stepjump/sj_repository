using LibrayApplication.Models;
using System.Data.Entity;

namespace LibrayApplication.Context
{
    public class LibraryDb : DbContext
    {
        public LibraryDb() : base("name=DBCS") { }

        public DbSet<Book> Books { get; set; }
    }
}