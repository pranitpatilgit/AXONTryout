package com.pranit.query

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id

@Entity
data class ProductCartView(

        @Id
        val cartId : UUID,

        @ElementCollection(fetch = FetchType.EAGER)
        val products : MutableMap<UUID, Int>
)
{
        fun addProducts(productId: UUID, amount: Int){
                products.compute(productId) {productId, value -> (value?:0) + amount}
        }

        fun removeProducts(productId: UUID, amount: Int){
                val leftOverQuantity = products.compute(productId) {productId, value -> (value?:0) -amount}
                if(leftOverQuantity == 0){
                        products.remove(productId)
                }
        }
}

interface ProductCartViewRepository : JpaRepository<ProductCartView, UUID>