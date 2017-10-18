package jb.project.starwarsshoppinglist.service

/**
 * Created by Jb on 13/10/2017.
 */
object ProductRepositoryProvider {

        fun provideProductRepository(): ProductRepository {
            return ProductRepository(ProductListService.Factory.create())
        }
}