import React from 'react'
import { Button } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';

//  props variandi
function SortButtons(props) {
  const { t } = useTranslation(); 

  function sortAZ() {
    props.filteredProducts.sort((a, b) => a.name.localeCompare(b.name));
    props.setFilteredProducts(props.filteredProducts.slice());
    props.setProducts(props.filteredProducts.slice(20*props.page-20, 20*props.page));
  }

  function sortZA() {
    props.filteredProducts.sort((a, b) => b.name.localeCompare(a.name));
    props.setFilteredProducts(props.filteredProducts.slice());
    props.setProducts(props.filteredProducts.slice(20*props.page-20, 20*props.page));
  }

  function sortPriceAsc() {
    props.filteredProducts.sort((a, b) => a.price - b.price);
    props.setFilteredProducts(props.filteredProducts.slice());
    props.setProducts(props.filteredProducts.slice(20*props.page-20, 20*props.page));
  }

  function sortPriceDesc() {
    props.filteredProducts.sort((a, b) => b.price - a.price);
    props.setFilteredProducts(props.filteredProducts.slice());
    props.setProducts(props.filteredProducts.slice(20*props.page-20, 20*props.page));
  }

  return (
    <div>
      <Button variant="secondary" onClick={sortAZ}>{t("sort.sorteeriAZ")}</Button>
      <Button variant="secondary" onClick={sortZA}>{t("sort.sorteeriZA")}</Button>
      <Button variant="secondary" onClick={sortPriceAsc}>{t("sort.sorteeriKasvav")}</Button>
      <Button variant="secondary" onClick={sortPriceDesc}>{t("sort.sorteeriKahanev")}</Button>
    </div>
  )
}

export default SortButtons