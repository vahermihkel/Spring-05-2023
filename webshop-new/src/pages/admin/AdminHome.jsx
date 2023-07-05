import React from 'react'
import Button from 'react-bootstrap/Button';
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';

function AdminHome() {
  const { t } = useTranslation(); // i18n on nt keelevahetuseks

  return (
    <div>
      <Button variant="primary" as={Link} to="/admin/maintain-categories">{t("maintain-categories")}</Button>{' '}
      <Button variant="secondary" as={Link} to="/admin/maintain-shops">{t("maintain-shops")}</Button>{' '}
      <Button variant="success" as={Link} to="/admin/add-product">{t("add-product")}</Button>{' '}
      <Button variant="warning" as={Link} to="/admin/maintain-products">{t("maintain-products")}</Button>{' '}
      <Button variant="primary" as={Link} to="/admin/maintain-carousel">{t("maintain-carousel")}</Button>{' '}
      <Button variant="primary" as={Link} to="/admin/maintain-shops">{t("maintain-shops")}</Button>{' '}
      <Button variant="primary" as={Link} to="/admin/persons">{t("maintain-persons")}</Button>{' '}
      <Button variant="primary" as={Link} to="/admin/all-orders">{t("show-orders")}</Button>{' '}
    </div>
  )
}

export default AdminHome