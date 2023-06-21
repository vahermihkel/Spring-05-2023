import { Button } from '@mui/material';

// object destructuring variandi
function FilterButtons({dbProducts, setProducts, setActivePage, setFilteredProducts, categories}) {
  
  function filterByCategory(categoryClicked) {
    //              240 --> category === camping  ---- 60
    //              60 --> category === usb drive
    const result = dbProducts.filter(element => element.category === categoryClicked);
    setFilteredProducts(result);
    setProducts(result.slice(0,20));
    setActivePage(1);
  }

  function resetFilters() {
    setFilteredProducts(dbProducts.slice());
    setProducts(dbProducts.slice(0,20));
    setActivePage(1);
  }

  return (
    <div>
      {/* <Button variant="contained" onClick={() => filterByCategory("usb drive")}>USB drive</Button>
      <Button variant="contained" onClick={() => filterByCategory("memory bank")}>Memory bank</Button>
      <Button variant="contained" onClick={() => filterByCategory("camping")}>Camping</Button> */}
      {categories.map(e => 
        <Button key={e.name} variant="contained" onClick={() => filterByCategory(e.name)}>{e.name}</Button>
      )}
      <Button variant="contained" onClick={resetFilters}>Reset</Button>
    </div>
  )
}

export default FilterButtons