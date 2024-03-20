import { Separator } from "@/components/ui/separator.tsx";
import { SidebarNav } from "@/components/ui/sidebar-nar.tsx";
import SettingsAccountPage from "@/components/pages/setting/account/page.tsx";

const sidebarNavItems = [
  {
    title: "Account",
    href: "/setting",
  },
];

export default function SettingPage() {
  return (
    <>
      <div className="md:hidden">
        Current did not implement setting page for mobile view.
      </div>
      <div className="hidden space-y-6 p-10 pb-16 md:block">
        <div className="space-y-0.5">
          <h2 className="text-2xl font-bold tracking-tight">Settings</h2>
          <p className="text-muted-foreground">Manage your account settings.</p>
        </div>
        <Separator className="my-6" />
        <div className="flex flex-col space-y-8 lg:flex-row lg:space-x-12 lg:space-y-0">
          <aside className="-mx-4 lg:w-1/5">
            <SidebarNav items={sidebarNavItems} />
          </aside>
          <div className="flex-1 lg:max-w-2xl">
            <SettingsAccountPage />
          </div>
        </div>
      </div>
    </>
  );
}
